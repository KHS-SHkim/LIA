package com.project.LIA.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeclarationAjaxData {

    @JsonProperty("user")
    UserDomain user;

    @JsonProperty("reporter")
    UserDomain reporter;

    @JsonProperty("book")
    BookDomain book;

    @JsonProperty("declarationList")
    List<DeclarationDomain> list;
}
