package com.sopovs.moradanen.spring.boot.autoconfigure.web;

import io.undertow.Undertow;

import java.util.List;

import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerException;
import org.springframework.util.ReflectionUtils;

public class UndertowEmbeddedServletContainer implements EmbeddedServletContainer {

    private final Undertow undertow;

    public UndertowEmbeddedServletContainer(Undertow undertow) {
        this.undertow = undertow;
    }

    @Override
    public void start() throws EmbeddedServletContainerException {
        undertow.start();

    }

    @Override
    public void stop() throws EmbeddedServletContainerException {
        undertow.stop();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getPort() {
        List<Object> listeners = (List<Object>) ReflectionUtils.getField(
                ReflectionUtils.findField(Undertow.class, "listeners"), undertow);
        for (Object listener : listeners) {
            return (Integer) ReflectionUtils.getField(ReflectionUtils.findField(listener.getClass(), "port"), listener);
        }

        return 0;
    }
}