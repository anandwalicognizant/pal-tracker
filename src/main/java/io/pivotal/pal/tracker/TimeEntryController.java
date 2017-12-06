package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by aw169 on 12/5/17.
 */
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntriesRepo;
    private final CounterService counterService;
    private final GaugeService gauge;

    public TimeEntryController(TimeEntryRepository timeEntriesRepo,
                               CounterService counterService,
                               GaugeService gauge) {
        this.timeEntriesRepo = timeEntriesRepo;
        this.counterService = counterService;
        this.gauge = gauge;
    }


    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntry);
        counterService.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry timeEntry = timeEntriesRepo.find(id);
        if (timeEntry != null) {
            counterService.increment("TimeEntry.read");
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        counterService.increment("TimeEntry.listed");
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = timeEntriesRepo.update(id, timeEntry);
        if (updatedTimeEntry != null) {
            counterService.increment("TimeEntry.updated");
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        timeEntriesRepo.delete(id);
        counterService.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
