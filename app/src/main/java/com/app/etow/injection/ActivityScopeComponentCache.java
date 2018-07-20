package com.app.etow.injection;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.app.etow.injection.components.ActivityScopeComponent;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

public class ActivityScopeComponentCache {

    private static final String CACHE_KEY = "component_cache_key";
    private static final int MAX_CACHE_COMPONENT = 7;

    private final Map<Long, ActivityComponentWrapper> componentHashMap;

    @Inject
    public ActivityScopeComponentCache() {
        componentHashMap = new HashMap<>();
    }

    public long saveComponentInstance(@Nullable ActivityScopeComponent component, Bundle outState) {
        if (component == null) {
            return -1;
        }
        long stateKey = System.currentTimeMillis();
        outState.putLong(CACHE_KEY, stateKey);
        componentHashMap.put(stateKey, new ActivityComponentWrapper(component, stateKey));
        gcComponent();
        return stateKey;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    public ActivityScopeComponent restoreComponent(@Nullable Bundle outState) {
        if (outState == null) {
            return null;
        }
        long stateKey = outState.getLong(CACHE_KEY, -1);
        if (stateKey < 0) {
            return null;
        }
        return Optional.ofNullable(componentHashMap.remove(stateKey))
                .map(ActivityComponentWrapper::getComponent)
                .orElse(null);
    }

    private void gcComponent() {
        if (componentHashMap.size() <= MAX_CACHE_COMPONENT) {
            return;
        }
        Set<Long> keySet = new LinkedHashSet<>(componentHashMap.keySet());
        long currentTime = System.currentTimeMillis();
        for (Long key : keySet) {
            ActivityComponentWrapper wrapper = componentHashMap.get(key);
            if (wrapper.isTimeout(currentTime)) {
                componentHashMap.remove(key);
            }
        }
    }

    // should call in on destroy
    public void clearComponent(long componentId) {
        componentHashMap.remove(componentId);
    }

    public void clearCache() {
        componentHashMap.clear();
    }

    private static class ActivityComponentWrapper {
        private static final long MAX_TIMEOUT = 60 * 1000;

        private final ActivityScopeComponent component;
        private final long componentTime;

        public ActivityComponentWrapper(ActivityScopeComponent component, long componentTime) {
            this.component = component;
            this.componentTime = componentTime;
        }

        public ActivityScopeComponent getComponent() {
            return component;
        }

        public long getComponentTime() {
            return componentTime;
        }

        public boolean isTimeout(long currentTime) {
            return currentTime - componentTime > MAX_TIMEOUT;
        }
    }
}
