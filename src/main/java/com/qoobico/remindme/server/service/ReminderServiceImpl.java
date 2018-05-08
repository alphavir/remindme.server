package com.qoobico.remindme.server.service;

import com.qoobico.remindme.server.entity.Remind;
import com.qoobico.remindme.server.repository.RemindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private RemindRepository repository;
    public List<Remind> getAll() {
        return repository.findAll();
    }

    public Remind getByID(long id) {
        return repository.getOne(Long.valueOf(id));
    }

    public Remind save(Remind remind) {
        return repository.saveAndFlush(remind);
    }

    public void remove(long id) {
        repository.deleteById(Long.valueOf(id));
    }
}
