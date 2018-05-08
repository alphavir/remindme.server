package com.qoobico.remindme.server.controller;

import com.qoobico.remindme.server.entity.Remind;
import com.qoobico.remindme.server.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/reminder")
@ResponseBody
public class ReminderController {

    @Autowired
    private ReminderService service;

//    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @RequestMapping(value = "/reminders", method = RequestMethod.GET)
    @ResponseBody
    public List<Remind> getAllReminders(){
        //List<Remind> list = service.findAll();
        //return createMockRemind();

        System.out.println("enter in getAllReminders");
        return service.getAll();
    }

    @RequestMapping(value = "/reminders/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Remind getReminder(@PathVariable("id") long remindId){
        return service.getByID(remindId);
    }

    @RequestMapping(value = "/reminders", method = RequestMethod.POST)
    @ResponseBody
    public Remind saveReminder(@RequestBody Remind remind){
        return service.save(remind);
    }
    
    @RequestMapping(value = "/reminders/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@PathVariable("id") long remindId){
        service.remove(remindId);
        return;
    }
//    private Remind createMockRemind() {
//        Remind remind = new Remind();
//        remind.setId(1);
//        remind.setRemindDate(new Date());
//        remind.setTitle("My first remind");
//
//        return remind;
//    }
}
