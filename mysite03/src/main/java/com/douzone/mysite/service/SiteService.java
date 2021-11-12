package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class SiteService {
	@Autowired
	private SiteRepository siteRepository;

	public SiteVo getSite() {
		return siteRepository.find();
	}

	// update는 boolean으로 써야함 -> mysql에서 update 됐을 때 / 안됐을 때 체크
	public boolean update(SiteVo vo) {
		return siteRepository.update(vo);
	}
}
