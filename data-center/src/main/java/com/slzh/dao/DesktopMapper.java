package com.slzh.dao;

import com.slzh.model.desktop.AppInfo;
import com.slzh.model.desktop.DesktopInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>文件名称:${FILE_NAME}</p>
 * <p>文件描述:</p>
 * <p>版权所有: 版权所有(C)2013-2099</p>
 * <p>公 司:  </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 *
 * @author slzh_tech
 * @version 1.0
 * @since 2020/12/8 下午7:46
 */
@Repository
@Mapper
public interface DesktopMapper {
    List<String> getDesktopIdByUserId(@Param("userId") long userId);

    String getFavoritesIdByUserId(@Param("userId") long userId);

    String getToolIdByUserId(@Param("userId") long userId);

    List<DesktopInfo> getDesktopInfo(@Param("list") List<String> desktopIdList);

    List<Map<String, Object>> getAppInfoByFavoritesId(@Param("favoritesId") String favoritesId, @Param("appIds") List<String> appIds);

    List<Map<String, Object>> getAppInfoByToolId(@Param("toolId") String toolId,@Param("appIds")List<String> appIds);

    Map<String, Object> getAppInfoByToolIdAndAppId(@Param("toolId") String toolId, @Param("appId") String appId);

    int getCountOfAppInfoByToolId(@Param("toolId") String toolId);

    List<AppInfo> getAppInfoByDesktopId(@Param("list") List<String> desktopIdList, @Param("appIds") List<String> appIds);

    List<Integer> getFolderSequenceByDesktopId(@Param("desktopId") String desktopId);

    List<Integer> getAppSequenceByDesktopId(@Param("desktopId") String desktopId);

    List<Integer> getFavoritesAppSequenceByFavoritesId(@Param("favoritesId") String favoritesId);

    List<Map<String, Object>> getAppInfoByAppId(@Param("appId") String appId);

    List<Map<String, Object>> getNeedUpdateFolderSequenceAppInfo(@Param("desktopId") String desktopId, @Param("folderId") String folderId, @Param("folderSequence") int folderSequence);

    List<Map<String, Object>> getNeedUpdateSequenceAppInfo(@Param("desktopId") String desktopId, @Param("sequence") int sequence);

    List<Map<String, Object>> getFavoritesAppInfoByAppId(@Param("appId") String appId);

    List<Map<String, Object>> getNeedUpdateSequenceFavoritesAppInfo(@Param("favoritesId") String favoritesId, @Param("sequence") int sequence);

    int deleteFolderInfoByDesktopId(@Param("desktopId") String desktopId);

    int deleteAppInfoByDesktopId(@Param("desktopId") String desktopId);

    int deleteAppInfoByFavoritesId(@Param("favoritesId") String favoritesId);

    int deleteAppInfoByToolId(@Param("toolId") String toolId);

    int deleteAppInfoByAppIdForTool(@Param("appId") String appId);

    int deleteAppInfoByAppIdForDesktop(@Param("appId") String appId);

    int deleteAppInfoByAppIdForFavorites(@Param("appId") String appId);

    int deletePortalDesktop(@Param("userId") long userId, @Param("desktopId") String desktopId);

    int insertPortalDesktop(@Param("userId") long userId, @Param("desktopId") String desktopId, @Param("desktopName") String desktopName);

    int insertPortalFavorites(@Param("userId") long userId, @Param("favoritesId") String favoritesId);

    int insertPortalTool(@Param("userId") long userId, @Param("toolId") String toolId);

    int insertPortalFolder(@Param("list") List<Map<String, Object>> folderList);

    int insertPortalApp(@Param("list") List<Map<String, Object>> appList);

    int insertPortalFavoritesApp(@Param("list") List<Map<String, Object>> appList);

    int insertPortalToolApp(@Param("params") Map<String, Object> appMap);

    int updateFolderInfoByDesktopId(@Param("map") Map<String, Object> folderMap);

    int updateAppInfoByDesktopId(@Param("map") Map<String, Object> appMap);

    int updateDesktopNameByDesktopId(@Param("desktopId") String desktopId, @Param("desktopName") String desktopName);
}
