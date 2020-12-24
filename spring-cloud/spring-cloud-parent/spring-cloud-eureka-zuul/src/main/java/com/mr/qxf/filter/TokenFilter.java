package com.mr.qxf.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("----------------------run----------");

        //context ~= 核心!!!!

        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();

        System.out.println(request.getRequestURL() + ":" + request.getMethod());

        String token = request.getParameter("token");

        if(StringUtils.isEmpty(token)){
            System.out.println("token is null");
            context.setSendZuulResponse(false);//默认值为true
            context.setResponseStatusCode(401);
            return null;
        }

        System.out.println("token is ok");
            return null;
    }
}
