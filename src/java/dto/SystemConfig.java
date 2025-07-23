/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Admin
 */
public class SystemConfig {
    private int id;
    private String config_key;
    private String config_value;
    private String description;

    public SystemConfig(int id, String config_key, String config_value, String description) {
        this.id = id;
        this.config_key = config_key;
        this.config_value = config_value;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getConfig_key() {
        return config_key;
    }

    public String getConfig_value() {
        return config_value;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setConfig_key(String config_key) {
        this.config_key = config_key;
    }

    public void setConfig_value(String config_value) {
        this.config_value = config_value;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
