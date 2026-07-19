package com.charoite.web

import com.charoite.service.CommentService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/discussion")
class DiscussionController(private val commentService: CommentService) {

    @GetMapping
    fun discussion(@AuthenticationPrincipal principal: OAuth2User?, model: Model): String {
        val comments = commentService.findAllComments()
        model.addAttribute("comments", comments)
        if (principal != null) {
            model.addAttribute("user", principal)
        }
        return "discussion"
    }

    @PostMapping("/comments")
    fun createComment(
        @AuthenticationPrincipal principal: OAuth2User?,
        @RequestParam text: String,
        model: Model
    ): String {
        if (principal == null) return "redirect:/login"
        
        val googleId = principal.attributes["sub"] as? String ?: principal.name
        val name = principal.attributes["name"] as? String ?: "Anonymous"
        val avatarUrl = principal.attributes["picture"] as? String
        
        commentService.createComment(googleId, name, avatarUrl, text)
        model.addAttribute("comments", commentService.findAllComments())
        return "fragments/comments :: commentsList"
    }
}
