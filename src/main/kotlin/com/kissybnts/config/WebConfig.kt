package com.kissybnts.config

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.DataStoreFactory
import com.google.api.client.util.store.MemoryDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.InputStreamReader

@Configuration
class WebConfig {
    private val APPLICATION_NAME = "MeetingRoomManagement"
    private val SCOPES = listOf(CalendarScopes.CALENDAR_READONLY)

    @Bean
    fun transport(): HttpTransport {
        return GoogleNetHttpTransport.newTrustedTransport()
    }

    @Bean
    fun jsonFactory(): JsonFactory {
        return JacksonFactory.getDefaultInstance()
    }

    @Bean
    fun dataStoreFactory(): DataStoreFactory {
        return MemoryDataStoreFactory()
    }

    @Bean
    @Autowired
    fun authorize(transport: HttpTransport, jsonFactory: JsonFactory, dataStoreFactory: DataStoreFactory): Credential {
        val clientSecrets = GoogleClientSecrets.load(jsonFactory, InputStreamReader(this.javaClass.getResourceAsStream("/client_secret.json")))
        val flow = GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory, clientSecrets, SCOPES).setDataStoreFactory(dataStoreFactory).setAccessType("online").build()
        val credential = AuthorizationCodeInstalledApp(flow, LocalServerReceiver.Builder().setPort(9000).build()).authorize("user")
        return credential
    }

    @Bean
    @Autowired
    fun calendarService(transport: HttpTransport, jsonFactory: JsonFactory, authorize: Credential): Calendar {
        return Calendar.Builder(transport, jsonFactory, authorize).setApplicationName(APPLICATION_NAME).build()
    }
}