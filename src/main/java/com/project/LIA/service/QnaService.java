package com.project.LIA.service;

import com.project.LIA.domain.QnADomain;
import org.springframework.ui.Model;

import java.util.List;

public interface QnaService  {

    List<QnADomain> list();

    List<QnADomain> list(Integer page, Model model);

}
