package br.com.nataliabraz.orgs.extensions

import android.content.Context
import android.os.Build
import android.widget.ImageView
import br.com.nataliabraz.orgs.R
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load

fun ImageView.carregar(
    context: Context,
    imagem: String? = null,
    fallback: Int = R.drawable.imagem_padrao,
    error: Int = R.drawable.erro,
    placeholder: Int = R.drawable.placeholder
) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    load(imagem, imageLoader) {
        fallback(fallback)
        error(error)
        placeholder(placeholder)
    }
}