package com.kissybnts.controller

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class CalendarController @Autowired constructor(private val calendarService: Calendar){

    @GetMapping(consumes = arrayOf(APPLICATION_JSON_VALUE))
    fun get(): ResponseEntity<List<Event>> {
        val items = calendarService.events().list("primary").apply {
            maxResults = 10
            timeMin = DateTime(System.currentTimeMillis())
            orderBy = "startTime"
            singleEvents = true
        }.execute().items

        return ResponseEntity(items, HttpStatus.OK)
    }
}