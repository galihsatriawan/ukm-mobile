package id.shobrun.ukmmobile.utils

import id.shobrun.ukmmobile.models.entity.*

object FakeData {
    fun fakeEvent() = Event(
        "1", 2, "galih", "galih@gmail.com",
        "galih", "Event", "2019/10/19 3:29", "Malang",
        "gg.gg/maps", 9.0, 8.0, "085604044550", EventStatus.COMING_SOON.toString(), 0, 0
    )

    fun fakeEvents() = listOf<Event>(
        fakeEvent(),
        fakeEvent(),
        fakeEvent(),
        fakeEvent()
    )

    fun fakeInvitation() = Invitation(
        1, "2", "galih@gmail.com", "galih", "1", null
        , InvitationStatus.WAITING_FOR_COMING.toString(), "galih", "2019", 1, "galih", false
    )

    fun fakeInvitations() = listOf<Invitation>(
        fakeInvitation(),
        fakeInvitation(),
        fakeInvitation(),
        fakeInvitation(),
        fakeInvitation()
    )

    fun fakeParticipant() = Participant(
        "2",
        "steven@gmail.com",
        "steven",
        2,
        "galih",
        "galih@gmail.com",
        "08999",
        "Malang"
    )

    fun fakeParticipants() = listOf<Participant>(
        fakeParticipant(),
        fakeParticipant(),
        fakeParticipant(),
        fakeParticipant(),
        fakeParticipant(),
        fakeParticipant(),
        fakeParticipant()
    )

    fun fakeUser() =
        User(2, "galih", "galih@gmail.com", "galih", "085604044550", "Malang", "test", true)

}