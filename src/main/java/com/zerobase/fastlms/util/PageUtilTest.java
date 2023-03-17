package com.zerobase.fastlms.util;

public class PageUtilTest {

    /**
     * <a href='?pageIndex=1'>&lt;&lt;</a>
     * <a href='?pageIndex=1'>&lt;</a>
     * <a href='?pageIndex=1'>1</a>
     * <a href='?pageIndex=2'>2</a>
     * <a href='?pageIndex=3'>3</a>
     * <a href='?pageIndex=3'>&gt;</a>
     * <a href='?pageIndex=3'>&gt;&gt;</a>
     */
    public static void main(String[] args) {

        PageUtil pageUtil = new PageUtil(30, 10, 10, "");
        String htmlPager = pageUtil.pager();

        System.out.println(htmlPager);


    }





}
