package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 특정 테이블에 대해 쿼리를 수행 가능하다
 */
@Mapper
public interface MemberMapper {
    long selectListCount(MemberSearchParam parameter);
    List<MemberDto> selectList(MemberSearchParam parameter); // 매퍼 인터페이스에서 리턴되는 값을 일반적으로 파라미터로 함
}
