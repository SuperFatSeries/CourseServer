package com.dds.sfscourse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseInfoDto {
    @JsonProperty(value = "courseware_count")
    Integer coursewareCount;

    @JsonProperty(value = "dated_count")
    Integer datedCount;

    @JsonProperty(value = "homework_count")
    Integer homeworkCount;

    @JsonProperty(value = "submit_count")
    Integer submitCount;

    public CourseInfoDto(Integer coursewareCount, Integer datedCount, Integer homeworkCount, Integer submitCount) {
        this.coursewareCount = coursewareCount;
        this.datedCount = datedCount;
        this.homeworkCount = homeworkCount;
        this.submitCount = submitCount;
    }

    public Integer getCoursewareCount() {
        return coursewareCount;
    }

    public void setCoursewareCount(Integer coursewareCount) {
        this.coursewareCount = coursewareCount;
    }

    public Integer getDatedCount() {
        return datedCount;
    }

    public void setDatedCount(Integer datedCount) {
        this.datedCount = datedCount;
    }

    public Integer getHomeworkCount() {
        return homeworkCount;
    }

    public void setHomeworkCount(Integer homeworkCount) {
        this.homeworkCount = homeworkCount;
    }

    public Integer getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(Integer submitCount) {
        this.submitCount = submitCount;
    }
}
