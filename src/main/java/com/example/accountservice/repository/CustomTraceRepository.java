package com.example.accountservice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/13/2022
 */
@Repository
@Slf4j
public class CustomTraceRepository implements HttpTraceRepository {
    AtomicReference<HttpTrace> lastTrace = new AtomicReference<>();

    @Override
    public List<HttpTrace> findAll() {
        return Collections.singletonList(lastTrace.get());
    }

    @Override
    public void add(HttpTrace trace) {
        if ("GET".equals(trace.getRequest().getMethod())) {
            log.debug("{} -> {} \t Request Headers: {}\tResponse Headers: {}\tTaken Time: {};",
                    trace.getRequest().getMethod().toUpperCase(),
                    trace.getRequest().getUri(),
                    trace.getRequest().getHeaders(),
                    trace.getResponse().getHeaders(),
                    trace.getTimeTaken());
            lastTrace.set(trace);
        }
    }
}
