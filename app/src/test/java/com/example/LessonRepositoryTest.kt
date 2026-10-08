package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.LessonRepository
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class LessonRepositoryTest {

    @Test
    fun testInitialLessonsAndFreeCount() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = LessonRepository(context)

        val lessons = repo.allLessons
        assertEquals("15 levels must exist", 15, lessons.size)

        val freeLessons = lessons.filter { it.isFree }
        assertEquals(3, freeLessons.size)

        val profile = repo.getUserProfile()
        assertNotNull(profile)
        assertTrue(profile.unlockedLessonIds.contains("level_1"))
        assertTrue(profile.unlockedLessonIds.contains("level_2"))
        assertTrue(profile.unlockedLessonIds.contains("level_3"))
    }

    @Test
    fun testAchievementsAndLeaderboard() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = LessonRepository(context)
        val profile = repo.getUserProfile()

        val achievements = repo.getAchievements(profile)
        assertTrue("Achievements list should not be empty", achievements.isNotEmpty())

        val leaderboard = repo.getLeaderboard(profile)
        assertTrue("Leaderboard should contain peers and current user", leaderboard.size >= 10)
        val userEntry = leaderboard.find { it.isCurrentUser }
        assertNotNull("Current user must be in leaderboard", userEntry)
    }
}
