package cn.edu.upc.service.Impl;

import cn.edu.upc.dao.SpecificMapper;
import cn.edu.upc.pojo.Specific;
import cn.edu.upc.service.SpecificeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpecificeServiceImpl implements SpecificeService {
    @Resource
    private SpecificMapper specificMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Specific record) {
        return specificMapper.insert(record);
    }

    @Override
    public int insertSelective(Specific record) {
        return specificMapper.insertSelective(record);
    }

    @Override
    public Specific selectByPrimaryKey(Integer id) {
        return specificMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Specific record) {
        return specificMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Specific record) {
        return specificMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Specific> selectByCid(int cid) {
        return specificMapper.selectByCid(cid);
    }
}
