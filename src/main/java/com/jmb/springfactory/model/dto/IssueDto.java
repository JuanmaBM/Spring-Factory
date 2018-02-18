package com.jmb.springfactory.model.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class IssueDto extends BaseDto {

  private static final long serialVersionUID = 2454000008007887556L;

  @NotNull
  private String name;

  @NotNull
  private String description;

  private String status;

  @NotNull
  private UserDto reporter;

  private UserDto reviser;

  private List<WorkLogDto> workLogs;

  private List<CommentDto> comments;
}
