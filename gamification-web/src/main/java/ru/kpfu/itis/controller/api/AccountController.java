package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AccountProfileDto;
import ru.kpfu.itis.dto.BadgeDto;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.service.AccountBadgeService;
import ru.kpfu.itis.service.AccountInfoService;
import ru.kpfu.itis.service.AccountService;
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
    AccountBadgeService accountBadgeService;
    @Autowired
    RatingController ratingController;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AccountProfileDto getProfile(@PathVariable Long id) {
        Account account = accountService.findById(id);
        AccountInfo accountInfo = accountInfoService.findByAccount(account);
        ArrayList<Task> tasks = new ArrayList<>();
        for (AccountTask accountTask : account.getAccountTasks()) {
            if(accountTask.getTaskStatus().getType().equals(TaskStatus.TaskStatusType.COMPLETED))
                tasks.add(accountTask.getTask());
        }
        ArrayList<AccountBadge> badges = (ArrayList<AccountBadge>) accountBadgeService.findAllBadgesByAccount(account);

        //todo to pack all information on DTO

        AccountProfileDto profileDTO = packAccountProfileDto(account, accountInfo, tasks, badges);
        profileDTO.setRating_position(ratingController.getUserRating());
        return profileDTO;
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    public AccountProfileDto getMyProfile() {
        return getProfile(0L); //todo
    }


    private AccountProfileDto packAccountProfileDto(Account account, AccountInfo accountInfo,
                                                    ArrayList<Task> tasks, ArrayList<AccountBadge> badges) {
        AccountProfileDto profileDto = new AccountProfileDto();
        profileDto.setId(account.getId());
        profileDto.setLogin(account.getLogin());
        profileDto.setFirst_name(accountInfo.getFirstName());
        profileDto.setLast_name(accountInfo.getLastName());
        profileDto.setRating(accountInfo.getPoint());

        ArrayList<TaskDto> challengesDto = new ArrayList<>();
        for (Task accountTask : tasks) {
            TaskDto task = taskToDto(accountTask);
            challengesDto.add(task);
        }
        profileDto.setChallenges(challengesDto);
        challengesDto.clear();

        ArrayList<BadgeDto> badgesDto = new ArrayList<>();
        BadgeDto badge = new BadgeDto();
        for (AccountBadge accountBadge : badges) {
            badge.setName(accountBadge.getBadge().getName());
            badge.setDescription(accountBadge.getBadge().getDescription());
            badge.setImage(accountBadge.getBadge().getImage());
            badge.setType(accountBadge.getBadge().getType().name()); //todo
            for (Task task : accountBadge.getBadge().getTasks()) {
                TaskDto taskDto = taskToDto(task);
                challengesDto.add(taskDto);
            }
            badge.setChallenges(challengesDto);
        }
        profileDto.setBadges(badgesDto);
        return profileDto;
    }

    private TaskDto taskToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        //todo
        return taskDto;
    }
}
