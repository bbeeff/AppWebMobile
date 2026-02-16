// src/main/java/it/unicam/roombooker/dto/RoomDTO.java
package it.unicam.roombooker.dto;

public class RoomDTO {
    private Long id;
    private String name;
    private String building;
    private Integer capacity;

    public RoomDTO() {}

    public RoomDTO(Long id, String name, String building, Integer capacity) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.capacity = capacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}