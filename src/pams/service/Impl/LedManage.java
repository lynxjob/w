package pams.service.Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import pams.dao.LedDao;
import pams.model.Led;


@Component("ledManage")
public class LedManage {
	private LedDao leddao;

	
	public LedDao getLeddao() {
		return leddao;
	}
	@Resource(name="ledDaoImpl")
	public void setLeddao(LedDao leddao) {
		this.leddao = leddao;
	}
	public void save(Led led){
		this.leddao.save(led);
	}
	
	public void update(Led led){
		this.leddao.update(led);
	}
	public Led load(int shed_id,int pinghao){
		return this.leddao.load(shed_id, pinghao);
	}
	
}
