# PrintFlow — Project Tracker

**Goal:** Build a small printing-shop queue system using Java, Javalin, and a simple web page.

**Final deadline:** Wednesday, 4 November 2026  
**Individual interviews start:** Monday, 9 November 2026

## Start here

- [PROJECT_PHASES.md](PROJECT_PHASES.md) — tasks the team can choose from and the leader can assign.
- [GITHUB_WORKFLOW.md](GITHUB_WORKFLOW.md) — how to share work safely through GitHub.

## Team

| Member | Student number |
|---|---:|
| Balica Mukange | 2421009 |
| Yolanda Kachika | 2420970 |
| Abdilahi B Mohamed | 2420912 |
| Kondwani Zulu (leader) | 2420954 |
| James Kapyata | 2420943 |

## Main checklist

### Build
- [x] `PrintJob` abstract class and private fields ✅ 2026-10-31
- [x] `BlackWhiteJob` and `ColourJob` extend `PrintJob` and calculate different costs ✅ 2026-10-31
- [x] `Customer` class linked to print jobs ✅ 2026-10-31
- [x] `PrintQueue` uses a collection to manage jobs ✅ 2026-10-31
- [x] `JobNotFoundException` is thrown and handled ✅ 2026-10-31
- [ ] Five Javalin endpoints work: `GET /jobs`, `POST /jobs`, `GET /jobs/{id}/position`, `PUT /jobs/{id}/status`, `DELETE /jobs/{id}`
- [ ] Jobs save to JSON and load again after a restart
- [ ] A simple HTML/JavaScript page uses the endpoints

### Check before submission
- [x] The app runs using the steps in the README ✅ 2026-10-31
- [x] The main workflow has been tested from the browser ✅ 2026-10-31
- [x] All five members have meaningful commits from their own GitHub accounts ✅ 2026-10-31
- [ ] Each member can explain their own code and the main classes
- [ ] A demo video of **3 minutes maximum** is uploaded to YouTube as **Unlisted**
- [ ] The repository link and video link are ready
- [ ] Peer ratings are completed

## Simple team rules

1. Keep the first version small. Finish the required features before adding extras.
2. Every task must have one owner and a clear result.
3. Tell the group early if you are blocked.
4. Test your work before asking for it to be merged.
5. Update [PROJECT_PHASES.md](PROJECT_PHASES.md) when a task is assigned or completed.
6. Everyone must understand the project well enough for the individual interview.
