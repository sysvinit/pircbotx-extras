# pircbotx-extras

This is a small library intended to complement the functionality of the
[PircBotX](https://github.com/pircbotx/pircbotx) library. It contains a couple
of utility classes which build upon the PircBotX core, which I found I was
reimplementing every time I started a new project involving PircBotX.

## Building

This library depends on Java 8, builds using `maven` and depends
on the PircBotX library; at time of writing, it depends on the git
master version of PircBotX, which can either be downloaded from the
[Sonatype](https://oss.sonatype.org) repository or built from the project's
git repository using `mvn install`.

## API

This library provides three utility classes:

- `CallbackBotManager`: this is a subclass of `MultiBotManager`, except that it
  additionally supports running a callback function upon a bot's termination.
- `AddressableListener`: this is a `ListenerAdapter` which additionally
  supports triggering an event upon receipt of a message which addresses the
  bot by nick (e.g. a message of the form `botnick: message`).
- `AddressedMessageEvent`: the event object which is delivered when an
  addressed message is received.

Further documentation can be found within the Javadoc comments, which can
be rendered into HTML using the `mvn javadoc:javadoc` command.

## License

Copyright (C) 2019-2020 Molly Miller.

This project is licensed under the GNU General Public License, version 3
or later, as it is a *derived work* of the PircBotX library. Please see the
`COPYING` file for full license details.
