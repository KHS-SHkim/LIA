package com.project.LIA.service;

import com.project.LIA.domain.UserDomain;
import com.project.LIA.repository.UserRepository;
import com.project.LIA.util.U;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    UserRepository userRepository;

    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;
    @Value("${app.pagination.admin_rows}")
    private int PAGE_ROWS;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDomain> userList(Integer page, Model model) {

        if(page == null) page = 1;  // 디폴트는 1 page
        if(page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("page", page);

        Page<UserDomain> pageWrites = userRepository.findAll(PageRequest.of(page -1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();
        int totalPage = pageWrites.getTotalPages();

        if(page > totalPage) page = totalPage;

        int fromRow = (page - 1) * pageRows;

        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages -1;
        if(endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("writePages", writePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<UserDomain> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }
}
