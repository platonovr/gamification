package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AccountProfileDTO;
import ru.kpfu.itis.dto.BadgeDTO;
import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.service.AccountBadgeService;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.AccountService;
import ru.kpfu.itis.service.AccountTaskService;
import ru.kpfu.itis.util.Constant;

import java.util.ArrayList;

/**
 * Created by Rigen on 22.06.15.
 */

@RequestMapping(Constant.API_URI_PREFIX + "/user")
@RestController("apiAccountController")
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountInfoService accountInfoService;
    @Autowired
    AccountTaskService accountTaskService;
    @Autowired
    AccountBadgeService accountBadgeService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AccountProfileDTO getProfile(@PathVariable Long id) {
        Account account = accountService.findById(id);
        AccountInfo accountInfo = accountInfoService.findByAccount(account);
        ArrayList<AccountTask> tasks = (ArrayList<AccountTask>) accountTaskService.findCompleteTasksByAccount(account);
        ArrayList<AccountBadge> badges = (ArrayList<AccountBadge>) accountBadgeService.findAllBadgesByAccount(account);

        //todo rating_position
        //todo to pack all information on DTO

        AccountProfileDTO profileDTO = packAccountProfileDto(account, accountInfo, tasks, badges);
        return profileDTO;
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    public AccountProfileDTO getMyProfile() {
        return getProfile(0L); //todo
    }


    private AccountProfileDTO packAccountProfileDto(Account account, AccountInfo accountInfo,
                                                    ArrayList<AccountTask> tasks, ArrayList<AccountBadge> badges) {
        AccountProfileDTO profileDTO = new AccountProfileDTO();
        profileDTO.setId(account.getId());
        profileDTO.setLogin(account.getLogin());
        profileDTO.setFirst_name(accountInfo.getFirstName());
        profileDTO.setLast_name(accountInfo.getLastName());
        profileDTO.setRating(accountInfo.getPoint());

        ArrayList<TaskDTO> challengesDto = new ArrayList<>();
        for (AccountTask accountTask : tasks) {
            TaskDTO task = taskToDto(accountTask.getTask());
            challengesDto.add(task);
        }
        profileDTO.setChallenges(challengesDto);
        challengesDto.clear();

        ArrayList<BadgeDTO> badgesDto = new ArrayList<>();
        BadgeDTO badge = new BadgeDTO();
        for (AccountBadge accountBadge : badges) {
            badge.setName(accountBadge.getBadge().getName());
            badge.setDescription(accountBadge.getBadge().getDescription());
            badge.setImage(accountBadge.getBadge().getImage());
            badge.setType(accountBadge.getBadge().getType().name());
            badge.setType(accountBadge.getBadge().getType().name());
            for (Task task : accountBadge.getBadge().getTasks()) {
                TaskDTO taskDTO = taskToDto(task);
                challengesDto.add(taskDTO);
            }
            badge.setChallenges(challengesDto);
        }
        profileDTO.setBadges(badgesDto);
        return profileDTO;
    }

    private TaskDTO taskToDto(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        //todo
        return taskDTO;
    }
}
