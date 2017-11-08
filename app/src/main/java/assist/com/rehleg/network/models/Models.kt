package assist.com.rehleg.network.models

/**
 * Created by mihai on 01.11.2017.
 */
data class TopPromotedItemsList(
        var TopPromotedItems: MutableList<TopPromotedItem> = mutableListOf(),
        var ConfigurationType: String = "",
        var PromotionConfigurationId: String = "",
        var PromotedAppCatalogId: String = "",
        var PromotionLists: List<Promotion> = emptyList()

)

data class TopPromotedItem(
        var Id: String = "",
        var Title: String = "",
        var CatalogType: String = "",
        var ItemType: String = "",
        var PrimaryImageUri: String = "",
        var ThumbnailUri: String = ""

)

data class Promotion(
        var Id: String = "",
        var ListType: String = "",
        var HeaderText: String = "",
        var Orientation: String = "",
        var Uri: String = ""
)

data class VideoItem(
        var Video: Video = Video(),
        var VideoFormats: List<VideoFormat> = emptyList(),
        var EnforceGeoBlocking: Boolean = false
)


data class Video(
        var Id: String = "",
        var Title: String = "",
        var Key: String = "",
        var Type: String = "",
        var TrackingId: String = "",
        var TypeTitle: String = "",
        var Description: String = "",
        var EntryDate: String = "",
        var ExitDate: String = "",
        var AgeFrom: String = "",
        var AgeTo: String = "",
        var ThemeId: String = "",
        var Theme: String = "",
        var ThemeTitle: String = "",
        var ThemeThumbnailUrl: String = "",
        var ThemeColor: String = "",
        var SubTheme: String = "",
        var SubThemeTitle: String = "",
        var SubThemeId: String = "",
        var Characters: List<String> = emptyList(),
        var PrimaryImageUrl: String = "",
        var SitecorePath: String = "",
        var ImageLargeProcessed: String = "",
        var ThumbnailProcessed: String = "",
        var VideoFileId: String = "",
        var VideoVersion: String = "",
        var SubFileId: String = "",
        var ForceSubtitles: String = "",
        var SeasonTitle: String = "",
        var Season: Int = 0,
        var Episode: Int = 0,
        var Part: Int = 0,
        var RequiresReadingSkills: Boolean = false,
        var ContentLanguage: String = "",
        var LengthInSeconds: String = "",
        var ProcessingLanguage: String = "",
        var NetstoragePath: String = "",
        var IsGeoBlocked: Boolean = false,
        var AllowedCountries: List<String> = emptyList()

)

data class VideoFormat(
        var Url: String = "",
        var Quality: String = "",
        var Format: String = ""
)