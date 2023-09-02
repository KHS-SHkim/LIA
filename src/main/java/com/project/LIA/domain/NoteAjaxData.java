package com.project.LIA.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NoteAjaxData {

    @JsonProperty("user")
    UserDomain user;

    @JsonProperty("receiver")
    UserDomain receiver;

    @JsonProperty("book")
    BookDomain book;

    @JsonProperty("noteList")
    List<NoteDomain> list;
}
