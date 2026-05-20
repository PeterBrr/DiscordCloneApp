import React from 'react';
import { 
  Compass, Plus, Search, MessageCircle, Bell, 
  Hash, Mic, MicOff, Headphones, PhoneOff, 
  ChevronDown, MessageSquare 
} from 'lucide-react';
import { motion } from 'motion/react';

// --- Global Constants & Mock Data ---
const COLORS = {
  bgBase: '#1E1F22',
  bgMain: '#313338',
  bgAlt: '#2B2D31',
  accent: '#5865F2',
  accentHover: '#4752C4',
  green: '#23A559',
  red: '#ED4245',
};

const IMAGES = {
  me: "https://images.unsplash.com/photo-1712599982295-1ecff6059a57?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwb3J0cmFpdCUyMG1hbiUyMHNtaWxpbmd8ZW58MXx8fHwxNzc5MjYxNTUwfDA&ixlib=rb-4.1.0&q=80&w=1080",
  valkyrie: "https://images.unsplash.com/photo-1580489944761-15a19d654956?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwb3J0cmFpdCUyMHdvbWFuJTIwc21pbGluZ3xlbnwxfHx8fDE3NzkxOTI0NTN8MA&ixlib=rb-4.1.0&q=80&w=1080",
  doggo: "https://images.unsplash.com/photo-1560731911-140d10257f19?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjb29sJTIwbWFzayUyMHBvcnRyYWl0fGVufDF8fHx8MTc3OTI2MTc4NHww&ixlib=rb-4.1.0&q=80&w=1080",
  neon: "https://images.unsplash.com/photo-1648736958777-a7a9479d72d8?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxuZW9uJTIwZ2FtZXIlMjBwb3J0cmFpdHxlbnwxfHx8fDE3NzkyNjE3ODN8MA&ixlib=rb-4.1.0&q=80&w=1080",
  server1: "https://images.unsplash.com/photo-1506260408121-e353d10b87c7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxmYW50YXN5JTIwbGFuZHNjYXBlfGVufDF8fHx8MTc3OTE1MTYzMXww&ixlib=rb-4.1.0&q=80&w=1080",
  server2: "https://images.unsplash.com/photo-1641650265007-b2db704cd9f3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxuZW9uJTIwY3liZXJwdW5rJTIwY2l0eXxlbnwxfHx8fDE3NzkyNjE1NTB8MA&ixlib=rb-4.1.0&q=80&w=1080",
  server3: "https://images.unsplash.com/photo-1733681198831-eb4b838c6f77?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxnYW1pbmclMjBsb2dvJTIwaWNvbnxlbnwxfHx8fDE3NzkyNjE1NTB8MA&ixlib=rb-4.1.0&q=80&w=1080",
  banner: "https://images.unsplash.com/photo-1614850716626-873413eb7c1b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjb2xvcmZ1bCUyMGFic3RyYWN0JTIwYmFubmVyfGVufDF8fHx8MTc3OTI2MTkwOHww&ixlib=rb-4.1.0&q=80&w=1080"
};

// --- Shared Components ---

const SharedSidebar = ({ activeId }: { activeId: string }) => {
  const servers = [
    { id: 's1', img: IMAGES.server1 },
    { id: 's2', img: IMAGES.server2 },
    { id: 's3', img: IMAGES.server3 },
  ];

  return (
    <div className="w-[72px] shrink-0 bg-[#1E1F22] flex flex-col items-center py-3 gap-2 overflow-y-auto h-full border-r border-[#111214]/50 z-20">
      <div className="relative group w-full flex justify-center mb-1">
        <div className={`absolute left-0 top-1/2 -translate-y-1/2 w-1 bg-white rounded-r-lg transition-all ${activeId === 'home' ? 'h-10' : 'h-0'}`} />
        <div className={`w-[48px] h-[48px] flex items-center justify-center transition-all ${activeId === 'home' ? 'rounded-[16px] bg-[#5865F2] text-white' : 'rounded-full bg-[#313338] text-gray-100'}`}>
          <Compass className="w-7 h-7" />
        </div>
      </div>
      <div className="w-8 h-[2px] bg-[#313338] rounded-full mb-1" />
      
      {servers.map(s => (
        <div key={s.id} className="relative group w-full flex justify-center">
          <div className={`absolute left-0 top-1/2 -translate-y-1/2 w-1 bg-white rounded-r-lg transition-all ${activeId === s.id ? 'h-10' : 'h-0'}`} />
          <div className={`w-[48px] h-[48px] transition-all overflow-hidden ${activeId === s.id ? 'rounded-[16px]' : 'rounded-full'}`}>
            <img src={s.img} alt="Server" className="w-full h-full object-cover" />
          </div>
        </div>
      ))}

      <div className="relative group w-full flex justify-center mt-1">
         <div className="w-[48px] h-[48px] rounded-full bg-[#313338] text-[#23A559] flex items-center justify-center">
           <Plus className="w-6 h-6" />
         </div>
      </div>
    </div>
  );
};

const SharedBottomNav = ({ activeTab }: { activeTab: string }) => {
  return (
    <nav className="h-[60px] shrink-0 bg-[#1E1F22] border-t border-[#111214]/50 flex items-center justify-around px-2 z-20">
      <div className={`flex flex-col items-center justify-center w-16 h-full gap-1 ${activeTab === 'chat' ? 'text-white' : 'text-gray-400'}`}>
        <MessageCircle className="w-6 h-6" fill={activeTab === 'chat' ? 'currentColor' : 'none'} />
        {activeTab === 'chat' && <div className="w-1 h-1 rounded-full bg-white" />}
      </div>
      <div className={`flex flex-col items-center justify-center w-16 h-full gap-1 text-gray-400 relative`}>
        <Bell className="w-6 h-6" />
        <div className="absolute top-2.5 right-4 w-2.5 h-2.5 bg-[#ED4245] border-2 border-[#1E1F22] rounded-full" />
      </div>
      <div className={`flex flex-col items-center justify-center w-16 h-full gap-1`}>
        <img src={IMAGES.me} alt="Profile" className="w-7 h-7 rounded-full object-cover ring-2 ring-transparent" />
      </div>
    </nav>
  );
};

const DeviceFrame = ({ children, title }: { children: React.ReactNode, title: string }) => (
  <div className="flex flex-col items-center gap-4">
    <h3 className="text-gray-400 font-bold uppercase tracking-widest text-sm">{title}</h3>
    <motion.div 
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="w-[340px] h-[740px] bg-[#313338] rounded-[2.5rem] border-[8px] border-[#0a0a0c] overflow-hidden relative shadow-2xl flex flex-col text-white ring-1 ring-white/10"
    >
      {children}
    </motion.div>
  </div>
);

// --- SVG Arrow Component ---
const FlowArrow = ({ start, end, curveOffset = 80 }: { start: any, end: any, curveOffset?: number }) => {
  const cp1x = start.x + curveOffset;
  const cp1y = start.y;
  const cp2x = end.x - curveOffset;
  const cp2y = end.y;
  const path = `M ${start.x} ${start.y} C ${cp1x} ${cp1y}, ${cp2x} ${cp2y}, ${end.x} ${end.y}`;

  // Angle for arrowhead
  const angle = Math.atan2(end.y - cp2y, end.x - cp2x) * 180 / Math.PI;

  return (
    <g className="animate-pulse" style={{ animationDuration: '3s' }}>
      <path 
        d={path} 
        fill="none" 
        stroke={COLORS.accent} 
        strokeWidth="4" 
        strokeLinecap="round" 
        strokeDasharray="8 8"
        className="drop-shadow-[0_0_8px_rgba(88,101,242,0.8)]" 
      />
      <circle cx={start.x} cy={start.y} r="6" fill={COLORS.accent} className="drop-shadow-[0_0_8px_rgba(88,101,242,1)]" />
      <polygon 
        points="-4,-8 12,0 -4,8" 
        fill={COLORS.accent} 
        transform={`translate(${end.x}, ${end.y}) rotate(${angle})`} 
        className="drop-shadow-[0_0_8px_rgba(88,101,242,1)]"
      />
    </g>
  );
};


// --- The 4 Core Screens ---

const Screen1Home = () => (
  <DeviceFrame title="Home / Direct Messages">
    <div className="flex flex-1 overflow-hidden">
      <SharedSidebar activeId="home" />
      <div className="flex-1 flex flex-col bg-[#313338]">
        <header className="h-[56px] flex items-center justify-between px-4 shrink-0">
          <h1 className="text-xl font-extrabold tracking-tight">Messages</h1>
          <Search className="w-5 h-5 text-gray-300" />
        </header>
        <div className="px-4 pb-2 flex gap-2">
          <span className="bg-[#404249] text-white px-3 py-1 rounded-full text-xs font-bold">Friends</span>
          <span className="text-gray-400 px-3 py-1 text-xs font-bold">Online</span>
        </div>
        <div className="flex-1 overflow-hidden flex flex-col p-2 gap-0.5">
          {[
            { n: 'Valkyrie', msg: 'ggs! see you tomorrow', img: IMAGES.valkyrie, t: '1h' },
            { n: 'Nova', msg: 'Check out this new build...', img: IMAGES.neon, t: '4h' },
            { n: 'DoggoGamer', msg: 'Woof woof! (I need heal...', img: IMAGES.doggo, t: '5h' },
          ].map((u, i) => (
            <div key={i} className="flex items-center gap-3 p-2 rounded-xl hover:bg-[#2B2D31]">
              <div className="relative shrink-0">
                <img src={u.img} className="w-11 h-11 rounded-full object-cover" alt="" />
                <div className="absolute bottom-0 right-0 w-3.5 h-3.5 bg-[#23A559] rounded-full border-2 border-[#313338]" />
              </div>
              <div className="flex-1 min-w-0">
                <div className="flex justify-between items-baseline mb-0.5">
                  <h3 className="font-bold text-[15px] truncate">{u.n}</h3>
                  <span className="text-[11px] text-gray-400 font-medium">{u.t}</span>
                </div>
                <p className="text-[13px] text-gray-400 truncate">{u.msg}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
    <SharedBottomNav activeTab="chat" />
  </DeviceFrame>
);

const Screen2Server = () => (
  <DeviceFrame title="Server Text Channel">
    <div className="flex flex-1 overflow-hidden">
      <SharedSidebar activeId="s1" />
      <div className="flex-1 flex flex-col bg-[#313338]">
        <header className="h-[56px] border-b border-[#1E1F22]/50 flex items-center px-4 shrink-0 justify-between shadow-sm z-10">
          <div className="flex items-center gap-1.5 text-white font-bold">
            <Hash className="w-5 h-5 text-gray-400" />
            <span className="text-[15px]">general-chat</span>
          </div>
        </header>

        {/* Voice Join Banner */}
        <div className="mx-3 mt-3 bg-[#23A559]/10 border border-[#23A559]/30 rounded-xl p-3 flex flex-col gap-2">
          <div className="flex items-center gap-2">
            <span className="text-[#23A559] font-bold text-[13px] flex items-center gap-1.5"><Mic className="w-4 h-4"/> General Voice</span>
          </div>
          <div className="flex items-center justify-between">
            <div className="flex -space-x-2">
              <img src={IMAGES.valkyrie} className="w-6 h-6 rounded-full border border-[#313338]" alt="" />
              <img src={IMAGES.neon} className="w-6 h-6 rounded-full border border-[#313338]" alt="" />
            </div>
            <button className="bg-[#23A559] hover:bg-[#1f8c4c] text-white px-4 py-1.5 rounded-md text-[13px] font-bold transition-colors">
              Join Voice
            </button>
          </div>
        </div>

        <div className="flex-1 p-4 flex flex-col gap-5 overflow-hidden">
          <div className="flex gap-3">
            <img src={IMAGES.valkyrie} className="w-10 h-10 rounded-full mt-0.5 object-cover shrink-0" alt=""/>
            <div>
              <div className="flex items-baseline gap-2 mb-0.5">
                <span className="font-bold text-[14px]">Valkyrie</span>
                <span className="text-[11px] text-gray-400">7:45 PM</span>
              </div>
              <p className="text-[14px] text-gray-200 leading-snug">Yes! 8 PM EST. Make sure you bring enough potions this time... 🤣</p>
            </div>
          </div>
          <div className="flex gap-3">
            <img src={IMAGES.neon} className="w-10 h-10 rounded-full mt-0.5 object-cover shrink-0" alt=""/>
            <div>
              <div className="flex items-baseline gap-2 mb-0.5">
                <span className="font-bold text-[14px]">Nova</span>
                <span className="text-[11px] text-gray-400">7:50 PM</span>
              </div>
              <p className="text-[14px] text-gray-200 leading-snug">I'll be there a bit late, start without me if you have to.</p>
            </div>
          </div>
        </div>

        <div className="p-3 shrink-0">
          <div className="bg-[#383A40] rounded-full h-10 flex items-center px-4 text-[14px] text-gray-400">
            Message #general-chat
          </div>
        </div>
      </div>
    </div>
    <SharedBottomNav activeTab="chat" />
  </DeviceFrame>
);

const Screen3Voice = () => (
  <DeviceFrame title="Active Voice Call">
    <div className="flex flex-1 overflow-hidden relative bg-black">
      <SharedSidebar activeId="s1" />
      
      <div className="flex-1 flex flex-col bg-gradient-to-b from-[#111214] to-[#1E1F22]">
        <header className="h-[56px] flex items-center justify-between px-4 shrink-0">
          <div className="flex items-center gap-1.5 font-bold text-[15px] text-[#23A559]">
            <Mic className="w-5 h-5" />
            <span>General Voice</span>
          </div>
          <ChevronDown className="w-5 h-5 text-gray-400" />
        </header>

        {/* 2x2 Grid */}
        <div className="flex-1 p-3 grid grid-cols-2 grid-rows-2 gap-3">
          {[
            { n: 'Me', img: IMAGES.me, spk: true },
            { n: 'Valkyrie', img: IMAGES.valkyrie, spk: false, mute: true },
            { n: 'Nova', img: IMAGES.neon, spk: false },
            { n: 'Doggo', img: IMAGES.doggo, spk: false }
          ].map((u, i) => (
            <div key={i} className="bg-[#2B2D31]/80 rounded-2xl flex flex-col items-center justify-center relative border border-[#313338]/50">
              <div className={`p-1 rounded-full ${u.spk ? 'ring-4 ring-[#23A559] bg-[#23A559]/20' : 'ring-2 ring-transparent'}`}>
                <img src={u.img} className="w-[68px] h-[68px] rounded-full object-cover" alt="" />
              </div>
              <span className="font-bold text-[13px] mt-2 text-gray-200">{u.n}</span>
              {u.mute && (
                <div className="absolute bottom-2 right-2 w-6 h-6 bg-[#ED4245] rounded-full flex items-center justify-center border-2 border-[#2B2D31]">
                  <MicOff className="w-3 h-3 text-white" />
                </div>
              )}
            </div>
          ))}
        </div>

        {/* Voice Controls */}
        <div className="h-[80px] bg-gradient-to-t from-[#111214] to-transparent flex items-center justify-center gap-4 shrink-0">
           <button className="w-12 h-12 rounded-full bg-[#313338] text-white flex items-center justify-center"><Headphones className="w-5 h-5"/></button>
           <button className="w-12 h-12 rounded-full bg-white text-gray-900 flex items-center justify-center"><MicOff className="w-5 h-5"/></button>
           <button className="w-14 h-14 rounded-full bg-[#ED4245] text-white flex items-center justify-center ml-2 shadow-lg shadow-[#ED4245]/20"><PhoneOff className="w-6 h-6"/></button>
        </div>
      </div>
    </div>
    <SharedBottomNav activeTab="chat" />
  </DeviceFrame>
);

const Screen4Profile = () => (
  <DeviceFrame title="User Profile Bottom Sheet">
    <div className="flex flex-1 overflow-hidden relative">
      {/* Blurred Background Mock */}
      <div className="absolute inset-0 opacity-40 blur-[3px] pointer-events-none flex">
        <SharedSidebar activeId="s1" />
        <div className="flex-1 bg-[#313338]" />
      </div>
      <div className="absolute inset-0 bg-black/50 z-10" />

      {/* Bottom Sheet */}
      <div className="absolute bottom-0 left-0 right-0 bg-[#232428] rounded-t-[24px] z-20 flex flex-col shadow-[0_-10px_40px_rgba(0,0,0,0.6)]">
        <div className="absolute top-2.5 left-1/2 -translate-x-1/2 w-10 h-1.5 bg-black/40 rounded-full z-30" />
        
        <div className="h-28 relative">
          <img src={IMAGES.banner} className="w-full h-full object-cover rounded-t-[24px]" alt=""/>
          <div className="absolute -bottom-8 left-4 rounded-full p-1.5 bg-[#232428]">
            <img src={IMAGES.valkyrie} className="w-[72px] h-[72px] rounded-full object-cover" alt=""/>
            <div className="absolute bottom-1 right-1 w-5 h-5 bg-[#23A559] rounded-full border-[3px] border-[#232428]" />
          </div>
        </div>

        <div className="px-5 pt-10 pb-6 flex flex-col gap-4">
          <div className="bg-[#111214] rounded-2xl p-4 border border-[#1E1F22]">
            <h2 className="text-xl font-extrabold text-white">Valkyrie</h2>
            <p className="text-gray-400 font-medium text-[13px] mb-3">@valk</p>
            <div className="w-full h-[1px] bg-[#2B2D31] mb-3" />
            <h3 className="text-[10px] font-bold uppercase text-gray-400 mb-1.5">About Me</h3>
            <p className="text-gray-200 text-[13px] leading-snug mb-4">
              Always down for some late-night raiding or coding sessions! 🚀👾
            </p>
            <h3 className="text-[10px] font-bold uppercase text-gray-400 mb-1.5">Roles</h3>
            <div className="flex gap-1.5">
               <span className="flex items-center gap-1.5 bg-[#2B2D31] px-2 py-1 rounded border border-[#1E1F22] text-[12px] font-bold text-gray-300"><div className="w-2 h-2 rounded-full bg-[#ED4245]"/>Admin</span>
               <span className="flex items-center gap-1.5 bg-[#2B2D31] px-2 py-1 rounded border border-[#1E1F22] text-[12px] font-bold text-gray-300"><div className="w-2 h-2 rounded-full bg-[#23A559]"/>Moderator</span>
            </div>
          </div>
          
          <button className="w-full bg-[#5865F2] text-white py-3 rounded-xl font-bold flex items-center justify-center gap-2 mt-1">
            <MessageSquare className="w-5 h-5"/> Send Message
          </button>
        </div>
      </div>
    </div>
  </DeviceFrame>
);


export default function App() {
  return (
    <div className="min-h-screen bg-[#111214] overflow-x-auto custom-scrollbar font-sans p-10 flex flex-col">
      <div className="mb-10 text-center sticky left-0 right-0">
        <h1 className="text-4xl font-extrabold text-white tracking-tight mb-2">User Flow Map: Discord Clone</h1>
        <p className="text-gray-400 font-medium">Dark Mode Gaming Chat App Components & Interactions</p>
      </div>

      {/* Main Canvas Area */}
      <div className="relative" style={{ width: '1760px', height: '800px', margin: '0 auto' }}>
        
        {/* SVG Arrow Connections Overlay */}
        <svg className="absolute inset-0 w-full h-full pointer-events-none z-50 overflow-visible">
          {/* S1 to S2: Originating from right edge of Server 1 Icon in Sidebar */}
          <FlowArrow start={{ x: 130, y: 220 }} end={{ x: 420, y: 220 }} curveOffset={100} />
          
          {/* S2 to S3: Originating from Voice Join button on S2 */}
          <FlowArrow start={{ x: 740, y: 172 }} end={{ x: 840, y: 172 }} curveOffset={40} />
          
          {/* S3 to S4: Originating from top right Grid Avatar on S3 */}
          <FlowArrow start={{ x: 1100, y: 215 }} end={{ x: 1260, y: 440 }} curveOffset={80} />
        </svg>

        {/* The 4 Screens Layout */}
        <div className="flex gap-[80px] w-full">
          <Screen1Home />
          <Screen2Server />
          <Screen3Voice />
          <Screen4Profile />
        </div>

      </div>

      <style dangerouslySetInnerHTML={{__html: `
        .custom-scrollbar::-webkit-scrollbar {
          height: 12px;
          width: 12px;
        }
        .custom-scrollbar::-webkit-scrollbar-track {
          background: #111214;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb {
          background: #313338;
          border-radius: 6px;
          border: 3px solid #111214;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb:hover {
          background: #404249;
        }
      `}} />
    </div>
  );
}
