/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL45C;

import java.nio.ByteBuffer;

/**
 * A version of {@link VertexBuffer} which allows uploading {@link ByteBuffer}s directly.
 *
 * This should probably be its own class (rather than subclassing), but I need access to {@link VertexBuffer#drawWithShader}.
 */
public class DirectVertexBuffer extends VertexBuffer
{
    private int actualIndexCount;

    public DirectVertexBuffer()
    {
        if( DirectBuffers.HAS_DSA )
        {
            RenderSystem.glDeleteBuffers( vertextBufferId );
            if( DirectBuffers.ON_LINUX ) BufferUploader.reset(); // See comment on DirectBuffers.deleteBuffer.
            vertextBufferId = GL45C.glCreateBuffers();
        }
    }

    public void upload( int vertexCount, VertexFormat.Mode mode, VertexFormat format, ByteBuffer buffer )
    {
        RenderSystem.assertOnRenderThread();

        DirectBuffers.setBufferData( GL15.GL_ARRAY_BUFFER, vertextBufferId, buffer, GL15.GL_STATIC_DRAW );

        this.format = format;
        this.mode = mode;
        actualIndexCount = indexCount = mode.indexCount( vertexCount );
        indexType = VertexFormat.IndexType.SHORT;
        sequentialIndices = true;
    }

    public void drawWithShader( Matrix4f modelView, Matrix4f projection, ShaderInstance shader, int indexCount )
    {
        this.indexCount = indexCount;
        drawWithShader( modelView, projection, shader );
        this.indexCount = actualIndexCount;
    }

    public int getIndexCount()
    {
        return actualIndexCount;
    }

    @Override
    public void close()
    {
        super.close();
        if( DirectBuffers.ON_LINUX ) BufferUploader.reset(); // See comment on DirectBuffers.deleteBuffer.
    }
}
