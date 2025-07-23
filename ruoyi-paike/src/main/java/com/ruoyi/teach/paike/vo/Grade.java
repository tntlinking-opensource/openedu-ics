package com.ruoyi.teach.paike.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Grade {
    private String name;
    private List<Course> courses = new ArrayList<>();
    private List<Adminclass> adminclasses = new ArrayList<>();
}
