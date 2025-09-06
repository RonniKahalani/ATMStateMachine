# ATM State Machine
This simple Java app, used in computer science classes, illustrates the different states an ATM undergoes and how it could be coded as a prototype.

There are 2 solution examples 
- The Bad - All code in one class).
- The Good - Is split up into a better, cleaner and more testable component architecture, seen from a code quality perspective like the 5 SOLID principles. 

## Purpose
Trying to present a real world device and identify the states the device goes through and what happens in the different states.
Also help the student understand UML tools line PlantUHL (effective text based UML modelling) and Visual Paradigm (graphical interface)

## Learning Process
Give every student a chance to think on their own in silence with no digital device, trying to identify the different states an ATM switches between.
After some time the student gather in their teams and share their findings

## Introduction
Explain the UML State Machine diagram its notations, transitions, activities/actions, conditions and alike.

![State Notations](/images/state-notations.png)

- [Visual Paradigm UML State Diagram](https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-state-machine-diagram/)
- [UML2 State Diagram](https://sparxsystems.com/resources/tutorials/uml2/state-diagram.html)

Ex. For each state identified, write down:
- Name
- Description
- Actions/Activities (entry, do, exit) 
- Transitions: Trigger/[Guard]/Effect conditions (for each transition leaving a state, with print message (for the user).

Ex. of transition notation, using PlantUML State notation syntax: [PlantUML Language Reference Guide](https://plantuml.com/guide)

From State (originator) --> To State (target) : Activity [condition] / Print message to the user.

- CARD_INSERTED : entry / Awaiting PIN
- CARD_INSERTED --> PIN_VERIFIED : ENTER_PIN [valid PIN] / print "PIN verified"
- CARD_INSERTED --> CARD_INSERTED : ENTER_PIN [invalid PIN] / print "Invalid PIN"
- CARD_INSERTED --> IDLE : EJECT_CARD / print "Card ejected"

## Step by Step
### Step 1 - For (up to) 5 minutes (NO TALKING)
- Everyone is on their own, which eliminates bias and priming, so every student use their max thinking capacity.
- Every student spends 5 minutes, in silence with no digital devices, trying to identify the different states an ATM switches between.
- The student writes down every state and transition they found.

### Step 2: Share your data with your team
- Every team member introduces their findings.
- The team compromises their finding into one final diagram, using either PlantUML or Visual Paradigm.

## Plugins in Intellij
- Go to Settings/Plugins
- Install plugins:
  - platuml4intellij
  - Markdown (if not bundled with the plantuml4intellij)

![Intellij Plugins](/images/intellij-plugins.png)