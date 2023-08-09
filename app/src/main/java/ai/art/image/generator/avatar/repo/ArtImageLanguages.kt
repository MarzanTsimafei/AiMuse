package ai.art.image.generator.avatar.repo

import androidx.annotation.DrawableRes

enum class ArtImageLanguages(
    /**
     * Created by Rodion Bartoshik on 09.09.2017.
     */
    private val language: String,  val selfName: String, val isRtl: Boolean = false) {
    ENGLISH("en",  "English"),
    RUSSIAN("ru",  "Русский"),
    SPANISH("es",  "Español"),
    PORTUGUESE("pt",  "Português"),
    FRENCH("fr",  "Français"),
    GERMAN("de",  "Deutsch"),
    ITALIAN("it",  "Italiano"),
    INDONESIAN("id",  "Indonesia"),
    TURKISH("tr", "Türkçe"),
    HEBREW("iw",  "עִבְרִית", true),
    POLISH("pl",  "język polski"),
    ARABIC("ar", "العربية", true),
    VIETNAMESE("vi",  "Tiếng Việt"),
    THAI("th",  "ภาษาไทย");

    // BULGARIAN("bg","bulgarian.png","български език");
    //  DANISH("da", "danish flag.png", "Dansk"),
    //    ROMANIAN("ro","romanian.png","Română"),
    //   CZECH("cs","czech.png","český jazyk"),
    //   HUNGARIAN("hu","hungarian.png","magyar"),
    //  NORWEGIAN("no", "norwegian flag.png", "Norsk"),
    //   MALAY("ms", "malay flag.png", "بهاس ملايو"),
    //   JAPANESE("ja", "japanese flag.png", "日本語"),
    //  KOREAN("ko", "korean flag.png", "한국어"),
    //  CHINESE("zh", "china flag.png", "汉语"),
    //  DUTCH("nl", "dutch flag.png", "Nederlands"),
    //   BENGALI("bn", "bengali flag.png", "বাংলা"),
    //  AFRIKAANS("af","afrikaans.png","Afrikaans"),
    //  AMHARIC("am","amharic.png","ኣማርኛ"),
    //  ARMENIAN("hy","armenian.png","հայերեն"),
    //  AZEIRBARJANI("az","azeirbarjani.png","azərbaycan dili"),
    //  BASQUE("eu","basque.png","euskara"), //   BURMESI("my","burmesi.png","ဗမာစာ"),
    //  CATALAN("ca","catalan.png","català"),
    //  CROATIAN("hr","croatian.png","hrvatski jezik"),
    //  ESTONIAN("et","estonian.png","eesti"),
    //  FILIPINO("fil","filipino.png","filipino"),
    //  FINNISH("fi","finnish.png","suomi"),
    // GALICIAN("gl","galician.png","Galego"),
    // GEORGIAN("ka","georgian.png","ქართული"),
    // GREEK("el","greek.png","ελληνικά"),
    //   ICELANDIC("is","icelandic.png","Íslenska"),
    //   KHMER("km","khmer.png","ខ្មែរ"),
    //   KYRGYZZ("ky","kyrgyzz.png","Кыргызча"),
    //   LAO("lo","lao.png","ພາສາລາວ"),
    //   LATVIAN("lv","latvian.png","Latviešu Valoda"),
    //   LITHUANIAN("lt","lithuanian.png","lietuvių kalba"),
    //   MACEDONIAN("mk","macedonian.png","македонски јазик"),
    //   MONGOLIAN("mn","mongolian.png","Монгол хэл"),
    //   NEPALI("ne","nepali.png","नेपाली"),
    //   PERSIAN("fa","persian.png","فارسی"),
    //   SERBIAN("sr","serbian.png","српски језик"),
    //   SINHALA("si","sinhala.png","සිංහල"),
    //   SLOVAK("sk","slovak.png","Slovenský Jazyk"),
    //   SLOVENIAN("sl","slovenian.png","Slovenski Jezik"),
    //   SWAHILI("sw","swahili.png","Kiswahili"),
    //   TAMIL("ta","tamil.png","தமிழ்"),
    /*  ZULU("zu","zulu.png","isiZulu")*/ // english, hindi, portuguese, russian, spanish, africaans(af), amharic(an), arabic(ar), armenian(hy), azeirbarjani(az), bangla(bn), basque(eu), bulgarian(bg), burmesi(my),catalan(ca), chinese(zh), croatian(hr), chezh(cs),
    // danish(da), dutch(nl), estonian(et), filippino(fil), finnish(fi), french(fr), galician(gl), georgian(ka), german(de), greek(el), hebrew(iw((he))), hungarian(hu),
    // islandic(is), indonesian(id((in))), italian(it), japanese(ja), kannada(kn), klmer(km),korea(ko), kyrgyzz(ky), lao(lo), latvian(lv), lithuanian(lt), macedonian(mk),
    // malay(ms), malayalam(ml), marathi(mr), mongolian(mn), nepali(ne), norwegian(no), persian(fa), polish(pl), romanian(ro),
    // romansh(rm), serbian(sr), sinhala(si), slovak(sk), slovenian(sl), swahili(sw), swedish(sv), tamil(ta), telugu(te), thai(th),
    // turkish(tr), vietnamese(vi), zulu(zu)

    override fun toString(): String {
        return language
    }


    companion object {
        fun fromString(pLanguage: String): ArtImageLanguages? {
            val `arr$` = values()
            val `len$` = `arr$`.size
            for (l in `arr$`) {
                if (l.toString() == pLanguage) {
                    return l
                }
            }
            return null
        }

        operator fun get(pos: Int): String {
            return values()[pos].toString()
        }

        /* public static StorageReference getImageRef(int pos)
    {
        return values()[pos].getImageRef();
    }*/
        fun max(): Int {
            return values().size - 1
        }
    }

}