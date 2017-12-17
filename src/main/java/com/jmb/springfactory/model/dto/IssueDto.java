package com.jmb.springfactory.model.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class IssueDto extends BaseDto {

  private static final long serialVersionUID = 2454000008007887556L;

  private String name;

  private String description;

  private String status;

  private UserDto reporter;

  private UserDto reviser;

  private List<WorkLogDto> workLogs;

  private List<CommentDto> comments;
}
