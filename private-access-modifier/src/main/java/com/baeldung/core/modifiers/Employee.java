package com.baeldung.core.modifiers;

public class Employee {

    private String privateId;
    public String name;
    private boolean manager;

    public Employee(String id, String name) {
        changeId(id);
        this.name = name;
    }

    private Employee(String id, String name, boolean managerAttribute) {
        this.privateId = id;
        this.name = name;
        this.privateId = id + "_ID-MANAGER";
    }

    public void changeId(String customId) {
        if (customId.endsWith("_ID")) {
            this.privateId = customId;
        } else {
            this.privateId = customId + "_ID";
        }
    }

    public String getId() {
        return this.privateId;
    }

    public boolean isManager() {
        return this.manager;
    }

    public void elevateToManager() {
        if ("Carl".equals(this.name)) {
            setManager(true);
        }
    }

    private void setManager(boolean manager) {
        this.manager = manager;
    }

}
