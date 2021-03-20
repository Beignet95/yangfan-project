package com.ruoyi.project.ums.account.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.ums.account.domain.Account;
import com.ruoyi.project.ums.account.service.IAccountService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 账号Controller
 * 
 * @author Beignet
 * @date 2021-03-20
 */
@Controller
@RequestMapping("/ums/account")
public class AccountController extends BaseController
{
    private String prefix = "ums/account";

    @Autowired
    private IAccountService accountService;

    @RequiresPermissions("ums:account:view")
    @GetMapping()
    public String account()
    {
        return prefix + "/account";
    }

    @RequiresPermissions("ums:account:view")
    @GetMapping("/site")
    public String site(String accountId,ModelMap mmap)
    {
        mmap.addAttribute("accountId",accountId);
        return prefix + "/site";
    }

    /**
     * 查询账号列表
     */
    @RequiresPermissions("ums:account:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Account account)
    {
        startPage();
        List<Account> list = accountService.selectAccountList(account);
        return getDataTable(list);
    }

    /**
     * 导出账号列表
     */
    @RequiresPermissions("ums:account:export")
    @Log(title = "账号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Account account)
    {
        List<Account> list = accountService.selectAccountList(account);
        ExcelUtil<Account> util = new ExcelUtil<Account>(Account.class);
        return util.exportExcel(list, "account");
    }

    /**
     * 新增账号
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存账号
     */
    @RequiresPermissions("ums:account:add")
    @Log(title = "账号", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Account account)
    {
        return toAjax(accountService.insertAccount(account));
    }

    /**
     * 修改账号
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Account account = accountService.selectAccountById(id);
        mmap.put("account", account);
        return prefix + "/edit";
    }

    /**
     * 修改保存账号
     */
    @RequiresPermissions("ums:account:edit")
    @Log(title = "账号", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Account account)
    {
        return toAjax(accountService.updateAccount(account));
    }

    /**
     * 删除账号
     */
    @RequiresPermissions("ums:account:remove")
    @Log(title = "账号", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(accountService.deleteAccountByIds(ids));
    }

    @Log(title = "账号导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("ums:account:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Account> util = new ExcelUtil<Account>(Account.class);
        List<Account> accountList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = accountService.importAccount(accountList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("ums:account:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<Account> util = new ExcelUtil<Account>(Account.class);
        return util.importTemplateExcel("账号");
    }

    /**
     * 查询账号列表
     */

    @RequiresPermissions("ums:account:list")
    @GetMapping("/getJson4AccountSite")
    @ResponseBody
    public String getJson4AccountSite(Account account)
    {
        String jsonString = accountService.getJson4AccountSite();
        return jsonString;
    }
}
