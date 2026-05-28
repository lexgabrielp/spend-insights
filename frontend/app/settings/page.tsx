import {Card} from '@/components/ui/card';
import {Nav} from '@/components/Nav';

export default function Settings() {
    return <main className="flex"><Nav/>
        <section className="flex-1 p-6"><h1 className="text-3xl font-black">Settings</h1><Card className="mt-6">
            <p>Ollama endpoint: local backend configuration</p><p>Default chat model: mistral:7b-instruct-q4_K_M</p>
            <p>Embeddings: nomic-embed-text</p></Card></section>
    </main>
}
