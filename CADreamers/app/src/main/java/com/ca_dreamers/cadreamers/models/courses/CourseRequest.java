package com.ca_dreamers.cadreamers.models.courses;

import java.io.Serializable;

public class CourseRequest implements Serializable {
    final String user_id;

    public CourseRequest(String user_id) {
        this.user_id = user_id;
    }
}
