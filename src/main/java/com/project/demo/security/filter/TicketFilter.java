package com.project.demo.security.filter;

import com.project.demo.excpetion.CustomError;
import com.project.demo.member.Service.SecurityMemberReadService;
import com.project.demo.member.domain.Member;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.security.domain.CustomUserDetail;
import com.project.demo.ticket.domain.Ticket;
import com.project.demo.ticket.repository.AdvanceTicketRepository;
import com.project.demo.ticket.repository.TicketRepository;
import com.project.demo.ticket.service.TicketService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class TicketFilter extends OncePerRequestFilter {


    public TicketFilter(RedisUserInfoService redisUserInfoService, AdvanceTicketRepository advanceTicketRepository) {
        this.redisUserInfoService = redisUserInfoService;
        this.advanceTicketRepository=advanceTicketRepository;
    }

    private final RedisUserInfoService redisUserInfoService;
    private final AdvanceTicketRepository advanceTicketRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/ticket");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        CustomUserDetail customUserDetail= (CustomUserDetail) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Member member=customUserDetail.getMember();
        //나중에 project id구하는 로직 추가.
        if(redisUserInfoService.checkExistProjectKey(member.getId(),1L)){
            filterChain.doFilter(request,response);
        }
        else{
           Optional<Ticket> t=advanceTicketRepository.findByMemberIdAndTicketId(member.getId(),1L);
           if(t.isEmpty()){
               sendErrorResponse(response,HttpStatus.BAD_REQUEST,"프로젝트 접근 권한이 없습니다");
               return;
           }
           redisUserInfoService.setUserProjectKey(member.getId(),1L);
            filterChain.doFilter(request,response);
        }
    }
    private void sendErrorResponse(HttpServletResponse response, HttpStatus httpStatus, String message) throws
            IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"message\":\"%s\"}", message));
    }
}
