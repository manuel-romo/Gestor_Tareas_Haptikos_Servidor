package com.haptikos.gestor_tareas_haptikos_servidor.model;

import java.io.Serializable;

public class MemberId implements Serializable {
    private String id;
    private String home;

    public MemberId() {}
    public MemberId(String id, String home) {
        this.id = id;
        this.home = home;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberId)) return false;
        MemberId that = (MemberId) o;
        return java.util.Objects.equals(id, that.id) && java.util.Objects.equals(home, that.home);
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, home);
    }
}