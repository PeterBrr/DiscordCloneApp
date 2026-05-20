package com.example.clonediscordapp.data;

import com.example.clonediscordapp.data.model.ChatMessage;
import com.example.clonediscordapp.data.model.DirectMessage;
import com.example.clonediscordapp.data.model.Server;
import com.example.clonediscordapp.data.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides hardcoded mock data matching the Discord Clone design.
 */
public final class MockData {

    private MockData() {}

    // --- Image URLs (from design) ---
    public static final String IMG_ME = "https://images.unsplash.com/photo-1712599982295-1ecff6059a57?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixlib=rb-4.1.0&q=80&w=400";
    public static final String IMG_VALKYRIE = "https://images.unsplash.com/photo-1580489944761-15a19d654956?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixlib=rb-4.1.0&q=80&w=400";
    public static final String IMG_DOGGO = "https://images.unsplash.com/photo-1560731911-140d10257f19?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixlib=rb-4.1.0&q=80&w=400";
    public static final String IMG_NEON = "https://images.unsplash.com/photo-1648736958777-a7a9479d72d8?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixlib=rb-4.1.0&q=80&w=400";
    public static final String IMG_SERVER1 = "https://images.unsplash.com/photo-1506260408121-e353d10b87c7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixlib=rb-4.1.0&q=80&w=400";
    public static final String IMG_SERVER2 = "https://images.unsplash.com/photo-1641650265007-b2db704cd9f3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixlib=rb-4.1.0&q=80&w=400";
    public static final String IMG_SERVER3 = "https://images.unsplash.com/photo-1733681198831-eb4b838c6f77?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixlib=rb-4.1.0&q=80&w=400";
    public static final String IMG_BANNER = "https://images.unsplash.com/photo-1614850716626-873413eb7c1b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixlib=rb-4.1.0&q=80&w=800";

    // --- Users ---
    public static final User ME = new User(
            "u0", "Me", "@me", IMG_ME,
            true, "Just coding things.", Collections.emptyList()
    );

    public static final User VALKYRIE = new User(
            "u1", "Valkyrie", "@valk", IMG_VALKYRIE,
            true,
            "Always down for some late-night raiding or coding sessions! \uD83D\uDE80\uD83D\uDC7E",
            Arrays.asList(
                    new User.Role("Admin", 0xFFED4245L),
                    new User.Role("Moderator", 0xFF23A559L)
            )
    );

    public static final User NOVA = new User(
            "u2", "Nova", "@nova", IMG_NEON,
            true, "Neon vibes only.", Collections.emptyList()
    );

    public static final User DOGGO = new User(
            "u3", "DoggoGamer", "@doggo", IMG_DOGGO,
            false, "Woof!", Collections.emptyList()
    );

    // --- Servers ---
    public static List<Server> getServers() {
        return Arrays.asList(
                new Server("s1", "Fantasy World", IMG_SERVER1),
                new Server("s2", "Cyber City", IMG_SERVER2),
                new Server("s3", "Gaming Hub", IMG_SERVER3)
        );
    }

    // --- Direct Messages ---
    public static List<DirectMessage> getDirectMessages() {
        return Arrays.asList(
                new DirectMessage(VALKYRIE, "ggs! see you tomorrow", "1h"),
                new DirectMessage(NOVA, "Check out this new build...", "4h"),
                new DirectMessage(DOGGO, "Woof woof! (I need heal...", "5h")
        );
    }

    // --- Chat Messages (for #general-chat) ---
    public static List<ChatMessage> getChatMessages() {
        return Arrays.asList(
                new ChatMessage("m1", VALKYRIE,
                        "Yes! 8 PM EST. Make sure you bring enough potions this time... \uD83E\uDD23",
                        "7:45 PM"),
                new ChatMessage("m2", NOVA,
                        "I'll be there a bit late, start without me if you have to.",
                        "7:50 PM")
        );
    }
}
