package com.kissybnts.controller

import com.kissybnts.entity.Calendar
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("calendar")
class CalendarController {

    @GetMapping(consumes = arrayOf(APPLICATION_JSON_VALUE))
    fun get(): ResponseEntity<List<Calendar>> {
        return ResponseEntity(listOf(), HttpStatus.OK)
    }
}