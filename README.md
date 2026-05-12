# Technion Tunes 🎵

A Java-based social music platform implementing song management, user relationships, rating systems, and graph-based social connectivity using advanced object-oriented programming principles.

Developed as part of an Object-Oriented Programming course project.

---

## Overview

Technion Tunes simulates a social music application where users can:

- Add and rate songs
- Build friendships
- Discover favorite songs
- Analyze shared musical interests
- Explore social connectivity between users

The project focuses on:

- Object-oriented architecture
- Java collections
- Graph traversal algorithms
- Encapsulation
- Data modeling
- Comparator-based sorting
- Stream processing

---

## Features

### User Management

- Add users
- Manage friendships
- Track favorite songs
- Compute average ratings
- Playlist statistics

### Song Management

- Add songs
- Rate songs
- Track raters
- Compute average ratings
- Sort songs dynamically

### Social Features

- Friendship graph management
- Shared favorite-song analysis
- BFS-based social connectivity
- User similarity detection

### Analytics

- Highest-rated songs
- Most-rated songs
- Top music likers
- Playlist statistics
- Rating analysis

---

## Technologies

- Java
- Object-Oriented Programming
- Java Collections Framework
- Java Streams
- BFS Graph Traversal

---

## Core Components

### `TechnionTunesImpl`

Main system manager responsible for:

- User management
- Song management
- Friendship graph
- Analytics operations

### `UserImpl`

Represents users and handles:

- Song ratings
- Favorite songs
- Friend relationships
- Playlist statistics

### `SongImpl`

Represents songs and manages:

- Ratings
- Average scores
- User raters
- Sorting logic

---

## Algorithms & Concepts

### BFS Social Connectivity

The system uses Breadth-First Search (BFS) to determine whether two users can connect through mutual friendships and shared favorite songs.

### Comparator-Based Sorting

Dynamic sorting is implemented for:

- Song rankings
- User rankings
- Rating analysis
- Playlist ordering

### Stream Processing

Java Streams are heavily used for:

- Filtering
- Sorting
- Aggregation
- Collection transformations

---

## Project Structure

```bash id="l6w4n3"
.
├── TechnionTunesImpl.java
├── UserImpl.java
└── SongImpl.java
