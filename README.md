# ViaAprilFools
ViaVersion addon to add support for some notable Minecraft snapshots.

ViaAprilFools is not usable by itself as a standalone software, as it is an addon for ViaVersion which adds more protocol translators.
ViaAprilFools is intended to be implemented in a ViaVersion based protocol translator.

If you are looking to implement ViaAprilFools in your own software you can start by reading the [Usage](#usage) section.  
If you just want to use ViaAprilFools yourself you can check out some projects which implement it in the [Projects](#projects-implementing-viaaprilfools) section.

### Added Server protocols
- 3D Shareware
- 20w14infinite
- Combat Test 8c

### Added Client protocols
- 3D Shareware

### Projects implementing ViaAprilFools
 - [ViaProxy](https://github.com/ViaVersion/ViaProxy): Standalone proxy which uses ViaVersion to translate between Minecraft versions. Allows Minecraft 1.7+ clients to join to any version server.
 - [ViaFabricPlus](https://github.com/ViaVersion/ViaFabricPlus): Fabric mod for the latest Minecraft version with QoL fixes and enhancements to the gameplay.
 - [ViaAprilFoolsPaper](https://github.com/malloryhayr/ViaAprilFoolsPaper): Paper platform implementation for ViaAprilFools. Allows the list of above client versions to join your server.

## Releases
### Gradle/Maven
To use ViaAprilFools with Gradle/Maven you can use the ViaVersion maven server:
```groovy
repositories {
    maven { url "https://repo.viaversion.com" }
}

dependencies {
    implementation("net.raphimc:ViaAprilFools:x.x.x") // Get latest version from releases
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
        <groupId>net.raphimc</groupId>
        <artifactId>ViaAprilFools</artifactId>
        <version>x.x.x</version> <!-- Get latest version from releases -->
    </dependency>
</dependencies>
```

### Jar File
If you just want the latest jar file you can download it from [GitHub Actions](https://github.com/RaphiMC/ViaAprilFools/actions/workflows/build.yml) or the [ViaVersion Jenkins](https://ci.viaversion.com/view/All/job/ViaAprilFools/).

## Usage
ViaAprilFools requires you to have an already functional ViaVersion implementation for your platform.
If you don't have one you can check out [ViaLoader](https://github.com/ViaVersion/ViaLoader) for an abstracted and simplified, but still customizable implementation.
You can also go to the other [ViaVersion](https://github.com/ViaVersion) repositories and look at their server and proxy implementations.

### Base Implementation
#### ViaAprilFools platform implementation
To get started you should create a class which implements the ViaAprilFools platform interface.
Here is an example:
```java
public class ViaAprilFoolsPlatformImpl implements ViaAprilFoolsPlatform {

    public ViaAprilFoolsPlatformImpl() {
        this.init(this.getDataFolder());
    }

    @Override
    public Logger getLogger() {
        return Via.getPlatform().getLogger();
    }

    @Override
    public File getDataFolder() {
        return Via.getPlatform().getDataFolder();
    }

}
```
This is a very basic implementation which just uses the ViaVersion logger and data folder.

#### Loading the platform
After you have created your platform implementation you should load it in your ViaVersion implementation.
Here is an example:
```java
Via.getManager().addEnableListener(ViaAprilFoolsPlatformImpl::new);
```
Make sure to add the enable listener before the Via manager is initialized (``((ViaManagerImpl) Via.getManager()).init();``).

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/ViaVersion/ViaAprilFools/issues).  
If you just want to talk or need help implementing ViaAprilFools feel free to join the ViaVersion
[Discord](https://discord.gg/viaversion).
