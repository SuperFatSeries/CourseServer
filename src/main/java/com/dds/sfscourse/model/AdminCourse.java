package com.dds.sfscourse.model;

public class AdminCourse extends Base {
    private Long adminId;
    private Long courseId;


    public AdminCourse(Long adminId, Long courseId) {
        this.adminId = adminId;
        this.courseId = courseId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "AdminCourse{" +
                "adminId=" + adminId +
                ", courseId=" + courseId +
                '}';
    }
}