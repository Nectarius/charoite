package com.example.charoite.web

import com.example.charoite.service.NoteService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/dashboard")
class NoteController(private val noteService: NoteService) {

    @GetMapping
    fun dashboard(@AuthenticationPrincipal principal: OAuth2User, model: Model): String {
        val googleId = principal.attributes["sub"] as? String ?: principal.name
        val notes = noteService.findNotesByUser(googleId)
        model.addAttribute("notes", notes)
        model.addAttribute("user", principal)
        return "dashboard"
    }

    @PostMapping("/notes")
    fun createNote(
        @AuthenticationPrincipal principal: OAuth2User,
        @RequestParam title: String,
        @RequestParam content: String,
        model: Model
    ): String {
        val googleId = principal.attributes["sub"] as? String ?: principal.name
        noteService.createNote(googleId, title, content)
        model.addAttribute("notes", noteService.findNotesByUser(googleId))
        return "fragments/notes :: notesList"
    }

    @DeleteMapping("/notes/{id}")
    fun deleteNote(
        @AuthenticationPrincipal principal: OAuth2User,
        @PathVariable id: String,
        model: Model
    ): String {
        val googleId = principal.attributes["sub"] as? String ?: principal.name
        noteService.deleteNote(googleId, id)
        model.addAttribute("notes", noteService.findNotesByUser(googleId))
        return "fragments/notes :: notesList"
    }

    @GetMapping("/notes/{id}/edit")
    fun editNoteForm(
        @AuthenticationPrincipal principal: OAuth2User,
        @PathVariable id: String,
        model: Model
    ): String {
        val googleId = principal.attributes["sub"] as? String ?: principal.name
        val note = noteService.getNote(googleId, id)
        if (note != null) {
            model.addAttribute("note", note)
            return "fragments/notes :: editForm"
        }
        return "redirect:/dashboard"
    }

    @PutMapping("/notes/{id}")
    fun updateNote(
        @AuthenticationPrincipal principal: OAuth2User,
        @PathVariable id: String,
        @RequestParam title: String,
        @RequestParam content: String,
        model: Model
    ): String {
        val googleId = principal.attributes["sub"] as? String ?: principal.name
        noteService.updateNote(googleId, id, title, content)
        model.addAttribute("notes", noteService.findNotesByUser(googleId))
        return "fragments/notes :: notesList"
    }

    @GetMapping("/notes/list")
    fun listNotes(
        @AuthenticationPrincipal principal: OAuth2User,
        model: Model
    ): String {
        val googleId = principal.attributes["sub"] as? String ?: principal.name
        model.addAttribute("notes", noteService.findNotesByUser(googleId))
        return "fragments/notes :: notesList"
    }
}
