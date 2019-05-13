# Worldwide News App

An App that shows Upcoming Movies and has a simple name search made for the ArcTouch code challenge.

## CI

For every push in the master, a job will run in bitrise. For more information: https://app.bitrise.io/app/9e6b8e00f32ae244

Master: [![Build Status](https://app.bitrise.io/app/ed152af89d6ea656/status.svg?token=ljB2W50XQsDWMqft6vGhyA)](https://app.bitrise.io/app/ed152af89d6ea656)

## Tools and Frameworks used

- MVVM
- Retrofit
- Koin (DI) 
- RxAndroid
- Glide (Image loading) 
- Architecture Components (LiveData and ViewModel)
- JUnit
- JaCoCo (code coverage)
- Espresso

## Tests

To run Unit tests with coverage:

    ./gradlew jacocoTestDebugUnitTestReport


To run Instrumentation tests:

    ./gradlew connectedDebugAndroidTest
    
All data for Unit and Instrumentation Tests are mocked with static JSON (check [MockedApiService.kt](app/src/testCommon/java/com/arctouch/codechallenge/base/mock/MockedEndpointService.kt) for implementation)


## Next steps

- Increase UI Tests coverage
- Configure Idling Resources for Espresso
- Improve layout
- Load more information in Movie details (more images, videos, etc.)
- Improve Movie Search with more filters
- New sections (Discover, TV Shows, Reviews, Rating System, etc.)


## Questions

For any questions, send an email to <wrmlopesjr@gmail.com>