package br.com.nataliabraz.orgs.extensions

import android.content.Context
import android.os.Build
import android.widget.ImageView
import br.com.nataliabraz.orgs.R
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load

fun ImageView.carregar(context: Context, imagem: String?) {
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
        fallback(R.drawable.erro)
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}