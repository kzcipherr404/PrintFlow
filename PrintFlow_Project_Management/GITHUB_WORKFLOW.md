# PrintFlow — GitHub Workflow (Simple Guide)

GitHub is where we keep the shared project. Each person works on their own task and sends it for review before it becomes part of the main version.

## The simple process

1. **Get the latest work.** Before starting, open a terminal in the PrintFlow folder and run:

   ```bash
   git switch main
   git pull origin main
   ```

2. **Create a branch for your task.** Use a short name that says what you are doing:

   ```bash
   git switch -c feature/print-job-class
   ```

   Change `feature/print-job-class` to match your task. A branch is your own working copy, so unfinished work does not immediately change `main`.

3. **Do the task.** Edit the files in your editor or Obsidian. Run the app or test the changes that you made.

4. **Check what changed.**

   ```bash
   git status
   git diff
   ```

5. **Save your work as a commit.** Replace the example path with the files you changed:

   ```bash
   git add path/to/your-file
   git commit -m "feat: add print job class"
   ```

   A commit is a saved record of your work. Use a message that explains the change.

6. **Upload your branch to GitHub.**

   ```bash
   git push -u origin feature/print-job-class
   ```

7. **Open a pull request on GitHub.** On the repository page, GitHub should offer a button to create a pull request from your branch. Explain what you changed and ask a teammate to review it.

8. **Wait for the review.** The reviewer checks that the code makes sense and does not break the project. Fix any issues they point out.

9. **Merge after approval.** Once it is checked, merge the pull request into `main`. Then everyone can get the updated version using `git pull origin main`.

## The words in plain English

- **Repository:** the shared project folder on GitHub.
- **Main:** the shared version that should stay working.
- **Branch:** a separate copy where you work on one task.
- **Commit:** a saved record of your changes.
- **Push:** send your commits to GitHub.
- **Pull:** bring the latest shared changes onto your computer.
- **Pull request (PR):** ask the group to review and add your work to `main`.
- **Merge:** add the approved changes to `main`.

## Team rules

- Do not all edit and push directly to `main` at the same time.
- Work on the task assigned to you. Tell the group if you need to change it.
- Keep your task small and ask for help early if you are stuck.
- Test your changes before opening a pull request.
- Review teammates' work when asked.
- Every member must make meaningful commits from their own GitHub account.
- Never upload passwords, access tokens, or private customer details.

## If you use Obsidian for Markdown

Save your Markdown files inside the local PrintFlow repository folder. After editing, follow steps 4–9 above to review, commit, push, and merge the changes. Track your task in [PROJECT_PHASES.md](PROJECT_PHASES.md).
