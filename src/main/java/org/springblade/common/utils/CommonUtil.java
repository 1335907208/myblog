package org.springblade.common.utils;

import org.springblade.core.tool.utils.WebUtil;

public class CommonUtil {
    public static CommonUtil build() {
        return new CommonUtil();
    }
    public Long getProjectId() {
        Long projectId = new Long(0);
        try {
            String projectIdStr = WebUtil.getRequest().getHeader("projectId");
            if (projectIdStr != null) {
                projectId = Long.parseLong(projectIdStr.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectId;
    }
    public String getProjectIdStr() {
        try {
            String projectIdStr = WebUtil.getRequest().getHeader("projectId");
            if (projectIdStr != null) {
                return projectIdStr.trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }
    public String getAgencyIdStr() {
        try {
            String agencyId = WebUtil.getRequest().getHeader("agencyId");
            if (agencyId != null) {
                return agencyId.trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }
}
