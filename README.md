# ViaAprilFools
ViaVersion addon to add support for some notable Minecraft snapshots.

ViaAprilFools itself runs on Paper and Velocity servers and can be implemented in any other platform which supports ViaVersion.

If you are looking to implement ViaAprilFools in your own software you can start by reading the [Usage](#usage) section.  
If you just want to use ViaAprilFools yourself you can check out some projects which implement it in the [Projects](#projects-implementing-viaaprilfools) section.

### Added Server protocols
- 3D Shareware
- 20w14infinite
- Combat Test 8c
- 25w14craftmine

### Added Client protocols
- 3D Shareware
- Combat Test 8c
- 25w14craftmine

### Projects implementing ViaAprilFools
 - [ViaProxy](https://github.com/ViaVersion/ViaProxy): Standalone proxy which uses ViaVersion to translate between Minecraft versions. Allows Minecraft 1.7+ clients to join to any version server.
 - [ViaFabricPlus](https://github.com/ViaVersion/ViaFabricPlus): Fabric mod for the latest Minecraft version with QoL fixes and enhancements to the gameplay.
 - [ViaFabric](https://github.com/ViaVersion/ViaFabric): Client-side and server-side ViaVersion implementation for Fabric

## Releases
### Gradle/Maven
To use ViaAprilFools with Gradle/Maven you can use the ViaVersion maven server:
```groovy
repositories {
    maven { url "https://repo.viaversion.com" }
}

dependencies {
    implementation("com.viaversion:viaaprilfools-common:x.x.x") // Get latest version from releases
}
```

```xml
<repositories>
    <repository>
        <id>viaversion</id>
        <url>https://repo.viaversion.com</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.viaversion</groupId>
        <artifactId>viaaprilfools-common</artifactId>
        <version>x.x.x</version> <!-- Get latest version from releases -->
    </dependency>
</dependencies>
```

### Jar File
If you just want the latest jar file you can download it from [GitHub Actions](https://github.com/RaphiMC/ViaAprilFools/actions/workflows/build.yml) or the [ViaVersion Jenkins](https://ci.viaversion.com/view/All/job/ViaAprilFools/).

## Usage
ViaAprilFools requires you to have an already functional ViaVersion implementation for your platform.
If you don't have one you can check out [this documentation](https://github.com/ViaVersion/ViaVersion/wiki/Creating-a-new-ViaVersion-platform) in order to create one.

### Base Implementation

#### Loading the platform
To implement ViaAprilFools you need to create a new instance of its platform implementation class ``ViaAprilFoolsPlatformImpl`` when ViaVersion is being enabled.
Here is an example:
```java
new ViaAprilFoolsPlatformImpl();
```
This should be done in your ``ViaManagerImpl.initAndLoad()`` method call as enable listener (or otherwise after the Via manager is initialized).

#### Note
If you want your platform to support the client protocols, you need the override the ``getClientProtocol`` function in your version provider:
```java
@Override
public ProtocolVersion getClientProtocol(UserConnection connection) {
    final ProtocolVersion version = connection.getProtocolInfo().protocolVersion();
    if (version.getVersionType() == VersionType.SPECIAL) {
        return ProtocolVersion.getProtocol(VersionType.SPECIAL, version.getOriginalVersion());
    } else {
        return super.getClientProtocol(connection);
    }
}
```
There is also a ``VAFServerVersionProvider`` which can act as wrapper class instead.

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/ViaVersion/ViaAprilFools/issues).  
If you just want to talk or need help implementing ViaAprilFools feel free to join the ViaVersion
[Discord](https://discord.gg/viaversion).
